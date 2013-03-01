<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
你发布了如下交易信息：<br/>
交易物品：<@showName player_item /><br/>
交易数量: ${player_item.exchangeAmount}<br/>
交易价格：${player_item.exchangePrice}分(${player_item.exchangePrice/100}金票)<br/>
<@gogame/>
</p></card>
</wml>