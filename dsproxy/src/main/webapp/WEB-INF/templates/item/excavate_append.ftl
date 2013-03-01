<#-- 显示可以洗掉的宝石 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if npc?exists>${npc.name}：</#if><#if dialog?exists && dialog.dialog?exists && dialog.dialog != "" >${dialog.dialog}
<#else>${last_npc.name}:
</#if>
<br/>
<#if desc?exists>${desc}<#else>你好,有什么可以为你效劳的吗?</#if>
<br/>
<#if player_item?exists>
<@showBeautiful player_item/><br/>
你想洗掉哪颗宝石? 洗掉装备上面的宝石需要挖孔符.<br/>
镶嵌(${player_item.appends?size}/${player_item.currHoleAmount}):<br/>
<#if player_item.appends?size==0>无<br/>
<#else>
<#list player_item.appends as append>		
	<@a href="/p?i_EX/${pid}/${player_item.id}/${append.id}/"/>洗掉</a> <@showSlaverUrl append.item/><br/>
</#list>
</#if>
</#if>
<br/>
<@gofacility/>
<@gogame/>
</p>
</card>
</wml>