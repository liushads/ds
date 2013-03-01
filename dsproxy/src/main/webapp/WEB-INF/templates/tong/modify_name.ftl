<#include "/include/header.ftl">
<card title="${game_title}"><p>
	<#if desc?exists>
	${desc}<br/>
	</#if>
	修改帮会名称<br/>
	<#if tong?exists>
	原有帮会名字：${tong.name}<br/>
	</#if>
	请输入新的帮名：<input type="text" name="name" value="" ></input><br/>
	<anchor>改名
	<@go href="/p?t_McN/${pid}/" method="post"/>
		<postfield name="1" value="$name" />
	</go>		
	</anchor>
	<br/>
	<@goback/>
	<@gogame/>
	</p>
</card>
</wml>