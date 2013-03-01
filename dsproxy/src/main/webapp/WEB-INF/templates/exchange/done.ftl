<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
交易成功，你花费了${gold/100}金票，向【${other.name}】购买了${amount}个<@showName player_item/><br/>
<@gogame/>
</p></card>
</wml>