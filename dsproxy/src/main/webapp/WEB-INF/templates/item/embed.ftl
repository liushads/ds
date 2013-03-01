<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${last_npc.name}:<br/>
<#if err_msg?exists>
${err_msg.text}<br/>
<#if err_msg.code = -25><@gopay/></#if>
<#if err_msg.code = -14>可以先去打孔</#if>
</#if>
<#if player_item?exists>镶嵌成功,你获得${player_item.item.name}
<@showPlayerItem player_item /><br/>
<@a href="/p?i_LM/${pid}/"/>继续镶嵌</a><br/>
</#if>
<@gofacility/>
</p></card>
</wml> 

 