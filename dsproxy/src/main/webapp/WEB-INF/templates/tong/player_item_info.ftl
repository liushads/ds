<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card id="c1"><p>
<@showPlayerItem player_item/>
<#if player_item.isUse==0>
	<#if player_item.amount==1><@a href="/p?t_D/${pid}/${player_item.id}/${player_item.item.id}/${player_item.item.type}/1"/>捐赠</a><#else><@a href="/p?t_DM/${pid}/${player_item.id}"/>捐赠</a></#if><br/>
</#if>
<@goback />
</p></card>
</wml>