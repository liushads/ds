<#include "/include/header.ftl">
<card title="${game_title}"><p>
	<#if desc?exists>
	${desc}<br/>
	</#if>
	创建公告：<br/>
	创建要求：标题小于20个字，内容小于200个字。<br/>
	标题：<input type="text" name="title" value="" ></input><br/>
	内容：<input type="text" name="content" value="" ></input><br/>
	<anchor>创建
	<@go href="/p?t_CN/${pid}/" method="post"/>
		<postfield name="1" value="$title" />
		<postfield name="2" value="$content" />
	</go>		
	</anchor>
	<br/>
	<@goback/>
	<@gogame/>
	</p>
</card>
</wml>