<#-- 显示可以进行属性转换的装备列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if last_npc?exists>${last_npc.name}:<br/></#if>
<#if player_item?exists>
	你需要将${player_item.item.name}装备的宝石拆卸?<br/>
	拆卸宝石需要拆卸符，拆卸后宝石会消失<br/>
	镶嵌${num}/${label_all}<br/>
	<#list player_item.appends as items>
		<@a href="/p?i_DHTr/${pid}/${player_item.id}/${items.id}/"/>拆卸</a> <@a href="/p?i_I/${pid}/${items.item.id}"/>${items.item.name}</a><br/>
	</#list>
<#else>
你没有可以进行拆卸装备,请确认装备已经装备到身上.<br/>
</#if>
<@goback />
<@gofacility/>
</p></card>
</wml> 