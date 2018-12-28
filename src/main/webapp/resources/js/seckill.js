//存放主要交互逻辑的js代码
//在这里将js模块化，类似于java中的包名.类名.方法名
var seckill = {
    //封装秒杀相关ajax的url
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        execution: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    //处理秒杀逻辑
    handleSeckill: function (seckillId, node) {
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            if (result && result['success']) {
                var exposer = result['data'];
                //通过Exposer实体中的布尔属性exposed判断是否开启秒杀
                if (exposer['exposed']) {
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    //只绑定一次点击事件，防止页面的重复点击
                    $('#killBtn').one('click', function () {
                        //执行秒杀请求
                        //1:先禁用按钮
                        $(this).addClass('disabled');
                        //2:发送秒杀请求
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {
                                var killRssult = result['data'];
                                var state = killRssult['state'];
                                var stateInfo = killRssult['stateInfo'];
                                //3:显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>')
                            }
                        });
                    });
                    node.show();
                } else {
                    //未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countdown(seckillId, now, start, end);
                }
            } else {
                console.log('result:' + result)
            }
        });
    },
    //验证手机号
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    countdown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        //时间判断
        if (nowTime > endTime) {
            seckillBox.html('秒杀结束!');
        } else if (nowTime < startTime) {
            var killTime = new Date(startTime);
            seckillBox.countdown(killTime, function (event) {
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown', function () {
                //计时完成后的回调事件：获取秒杀地址，执行秒杀
                seckill.handleSeckill(seckillId, seckillBox);
            });
        } else {
            //秒杀开始
            seckill.handleSeckill(seckillId, seckillBox);
        }
    },
    //详情页的秒杀逻辑
    detail: {
        //详情页的初始化
        init: function (param) {
            //手机验证和登录，计时的交互
            //规划我们的交互流程
            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            //调用验证手机号方法
            if (!seckill.validatePhone(killPhone)) {
                //绑定phone
                var killPhoneModel = $('#killPhoneModel');
                //显示弹出层
                killPhoneModel.modal({
                    show: true,//显示弹出层
                    backdrop: 'static',//禁止位置关闭
                    keyboard: false//关闭键盘事件
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if (seckill.validatePhone(inputPhone)) {
                        //验证通过，将电话写入cookie(有效期7天，只在/seckill路径下有效)
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        //刷新页面
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });
            }
            //已经登录的情况
            //计时交互
            var seckillId = param['seckillId'];
            var startTime = param['startTime'];
            var endTime = param['endTime'];
            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    //时间判断，计时交互
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                } else {
                    console.log('result:' + result)
                }
            });
        }
    }
}