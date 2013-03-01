<#-- 卖装备二次确认界面 -->
<#include "/include/header.ftl">
<#include "/include/item.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if last_npc?exists>${last_npc.name}:这可是个好东西哟,你确认要出售吗?<br/></#if>
<#if player_item?exists>
<@showNameUrl player_item/><br/>
<@a href="/p?i_SeI/${pid}/${player_item.id}/1/c"/>确认</a><br/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>