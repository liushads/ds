<#include "/include/header.ftl">
<card title="${game_title}"><p>
	<#if desc?exists>
	${desc}<br/>
	</#if>
	<br/>
	<@goback/>
	<@gogame/>
	</p>
</card>
</wml>