<#-- 装备分解二次确认 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${location}<br/>
${last_npc.name}:
<#if player_item?exists>
	你确定要溶解<@a href="/p?i_I/${pid}/${player_item.id}"/><@showName player_item/></a><br/>
	<@a href="/p?i_Dec/${pid}/${player_item.id}"/>确定</a><br/>
	<@a href="/p?i_LD/${pid}/"/>不,点错了</a><br/><#-- 跳转到分解列表 -->
<#else>
抱歉! 系统错误.
</#if>
<@goback />
<@gofacility/>
</p></card>
</wml> 