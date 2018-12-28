package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class detail_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/WEB-INF/jsp/common/head.jsp");
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <title>秒杀详情页</title>\r\n");
      out.write("    ");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("<!-- å¼å¥ Bootstrap -->\r\n");
      out.write("<link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n");
      out.write("<!-- HTML5 Shiv å Respond.js ç¨äºè®© IE8 æ¯æ HTML5åç´ ååªä½æ¥è¯¢ -->\r\n");
      out.write("<!-- æ³¨æï¼ å¦æéè¿ file:// å¼å¥ Respond.js æä»¶ï¼åè¯¥æä»¶æ æ³èµ·ææ -->\r\n");
      out.write("<!--[if lt IE 9]>\r\n");
      out.write("<script src=\"https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js\"></script>\r\n");
      out.write("<script src=\"https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js\"></script>\r\n");
      out.write("<![endif]-->");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("<!-- 页面显示部分 -->\r\n");
      out.write("<div class=\"container\">\r\n");
      out.write("    <!-- 头部 -->\r\n");
      out.write("    <div class=\"panel panel-default text-center\">\r\n");
      out.write("        <div class=\"panel-heading\">\r\n");
      out.write("            <h1>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${seckill.name}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</h1>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <!-- 身体 -->\r\n");
      out.write("    <div class=\"panel-body\">\r\n");
      out.write("        <h2 class=\"text-danger\">\r\n");
      out.write("            <!-- 显示time图标 -->\r\n");
      out.write("            <span class=\"glyphicon glyphicon-time\"></span>\r\n");
      out.write("            <!-- 展示倒计时 -->\r\n");
      out.write("            <span class=\"glyphicon\" id=\"seckill-box\"></span>\r\n");
      out.write("        </h2>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("<!-- 登录弹出层，输入电话 -->\r\n");
      out.write("<div id=\"killPhoneModel\" class=\"modal fade\">\r\n");
      out.write("    <div class=\"modal-dialog\">\r\n");
      out.write("        <div class=\"modal-content\">\r\n");
      out.write("            <div class=\"modal-header\">\r\n");
      out.write("                <h3 class=\"modal-title text-center\">\r\n");
      out.write("                    <span class=\"glyphicon glyphicon-phone\"></span>秒杀电话：\r\n");
      out.write("                </h3>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"modal-body\">\r\n");
      out.write("                <div class=\"row\">\r\n");
      out.write("                    <div class=\"col-xs-8 col-xs-offset-2\">\r\n");
      out.write("                        <input type=\"text\" name=\"killPhone\" id=\"killPhoneKey\" placeholder=\"填手机号\" class=\"form-control\">\r\n");
      out.write("                    </div>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("\r\n");
      out.write("            <div class=\"modal-footer\">\r\n");
      out.write("                <!-- 验证信息 -->\r\n");
      out.write("                <span id=\"killPhoneMessage\" class=\"glyphicon\"></span>\r\n");
      out.write("                <button type=\"button\" id=\"killPhoneBtn\" class=\"btn btn-success\">\r\n");
      out.write("                    <span class=\"glyphicon glyphicon-phone\"></span>\r\n");
      out.write("                    Submit\r\n");
      out.write("                </button>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("\r\n");
      out.write("<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->\r\n");
      out.write("<script src=\"https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js\"></script>\r\n");
      out.write("<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->\r\n");
      out.write("<script src=\"https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\r\n");
      out.write("<!-- jQuery cookie操作文件 -->\r\n");
      out.write("<script src=\"https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js\"></script>\r\n");
      out.write("<!-- jQuery countDowm倒计时插件 -->\r\n");
      out.write("<script src=\"https://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- 编写交互逻辑 -->\r\n");
      out.write("<script src=\"/resources/js/seckill.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("    $(function () {\r\n");
      out.write("        //使用EL表达式向JS文件中的方法传参\r\n");
      out.write("        seckill.detail.init({\r\n");
      out.write("            seckillId:");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${seckill.seckillId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write(",\r\n");
      out.write("            startTime:");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${seckill.startTime.time}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write(",//毫秒\r\n");
      out.write("            endTime:");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${seckill.endTime.time}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\r\n");
      out.write("        });\r\n");
      out.write("    });\r\n");
      out.write("</script>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
