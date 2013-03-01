<#include "/include/header.ftl">
<card title="${game_title}"><p>
通缉列表:
<#if missions?exists && missions?size &gt; 0 >
	<#list missions as mis> 
	 	<br/>${mis.targetName} : ${mis.completed}/${mis.maxPlayer}
	</#list>
	(完成次数/通缉击杀次数)
<#else>
<br/>没有通缉对象	
</#if>
<br/><@goback/>
<@gofacility/>
</p>
</card>
</wml>