<#include "/include/header.ftl">
<card title="${game_title}">
<p>
<#if sect?exists && uid?exists>
看来你立志要做一名无畏的${sect.name}，快给你的英雄取一个响亮的名字吧。<br/>
输入信息（限6字）<br/>
取名:
<input name="name" maxlength="6" emptyok="false" value="" type="text" /><br/>   
性别	: 
<select name="sex" > 
	<option value="0">女</option>
	<option value="1">男</option>
</select><br/>
<anchor>确认创建
	<@go href="/p?u_S/${uid}/" method="post"/>
		<postfield name="1" value="$name" />
		<postfield name="2" value="$sex" />
		<postfield name="3" value="${sect.id}" />
	</go>
</anchor>
<br/>
</#if>
<@ag href="/p?u_EZ/${uid}/"/>返回选择</a><br/>
</p></card>
</wml>