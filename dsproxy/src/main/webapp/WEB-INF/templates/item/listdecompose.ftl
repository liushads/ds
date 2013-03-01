<#-- 显示可以进行分解的装备列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${last_npc.name}:<br/>
<#if (items?size>0)>
	你身上就这些可以溶解,你要溶解哪一个?<br/>
	<#list items as pi>		
		<@a href="/p?i_DeP/${pid}/${pi.id}"/>分解</a>
		<@a href="/p?i_I/${pid}/${pi.id}"/><@showHorizontal pi/></a><br/>
	</#list>
<#else>
	很抱歉，你身上没有可分解的装备。如果要分解的装备正在使用,可以先卸下<br/>
</#if>
<@goback />
<@gofacility/>
</p></card>
</wml> 