<#-- 显示道具信息 -->
<#include "/include/header.ftl">
<#include "/include/item.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if desc?exists>${desc}<br/></#if>
<#if player_item?exists>
<@showPlayerItem player_item/>
<#-- 显示对道具的操作选项 -->
<#if (player_item.playerId == pid && !no_op?exists)>
	<#if player_item.isUse==1>
		<@a href="p?i_UnA/${pid}/${player_item.id}"/>卸下</a><br/>
	<#else>
		<#if (player_item.bindId=0) || (player_item.bindId == player_item.playerId) >
			<#if player_item.item.type == 1><#-- 装备类 -->
				<@a href="/p?i_UA/${pid}/${player_item.id}"/>使用</a><br/>
			</#if>
		</#if>
	</#if>
	<#if player_item.item.dropable>
		<@a href="/p?i_DrP/${pid}/${player_item.id}/${player_item.item.id}"/>丢弃</a><br/>
	</#if>
	<#if player_item.bindId == 0>
	<@a href="/p?i_BD/${pid}/${player_item.id}/"/>锁定</a><br/>
	<#else>
	<@a href="/p?i_UnBPre/${pid}/${player_item.id}/"/>解锁</a><br/>
	</#if>
</#if>
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml>