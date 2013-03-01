<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
你发布了如下交易信息：<br/>
交易宠物：<@a href="/p?pe_ReP/${pid}/${player_pet.id}"/>${player_pet.customName}</a><br/>
交易价格：${player_pet.money}分(${player_pet.money/100}金票)<br/>
<@gogame/>
</p></card>
</wml>