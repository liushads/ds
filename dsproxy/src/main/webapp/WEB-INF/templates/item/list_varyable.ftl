<#-- 显示可以进行属性转换的装备列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if last_npc?exists>${last_npc.name}:<br/></#if>
<#if items?exists>
	你需要将哪件装备特性变化?<br/>
	<#list items as playerItem>
		<@a href="/p?i_VPT/${pid}/${playerItem.id}/"/>改变</a> <@a href="/p?i_I/${pid}/${playerItem.id}"/><@outStar playerItem/>${playerItem.item.name}(${playerItem.item.level}级)</a><br/>
	</#list>
<#else>
你没有可以进行转换的装备,请确认装备已经装备到身上.<br/>
</#if>
<@goback />
<@gofacility/>
</p></card>
</wml> 