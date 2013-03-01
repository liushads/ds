<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
你买下了${num}个${goods.name},花费${needGold}。<br/>
<#if discount &gt; 0>
使用打折卡，享受了${discount/10}折<br/>
</#if>
账户信息:<br/>
<@showAllMoney player,-1/>
负重:${player.room}/${player.dyn.maxRoom}<br/>
<@gomall/>
<@gogame/>
</p></card>
</wml>