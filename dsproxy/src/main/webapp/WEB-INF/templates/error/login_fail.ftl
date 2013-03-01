<#include "/include/header.ftl">
<card title="${game_title}">
<p>
登陆失败，请检查您的用户名和密码是否输入正确。<br/>
<#-- 
<a href="http://mgtest.3g.qq.com/login.jsp?cpid=917&amp;gameid=127">重新登陆</a>
-->
<a href="${loginUrl?xml}">重新登陆</a>
</p>
</card>
</wml>