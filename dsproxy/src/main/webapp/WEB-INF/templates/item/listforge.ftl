<#-- 显示可以打造装备列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${location}<br/>
${last_npc.name}:
<#if items?exists>
	你好,有什么可以为你帮忙的吗?<br/>
	<#list items as item>
		<@showList item,'1'/>
	</#list>	
<#else>
	抱歉! 我这里什么东西都没有.<br/>
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml>