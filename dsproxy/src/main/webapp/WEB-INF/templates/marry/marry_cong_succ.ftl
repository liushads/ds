<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
祝贺【${playerMarry.playerName}】和【${playerMarry.mateName}】大喜成功，赠送礼金：${congType}<br/>
很感谢你带来的祝福，送给你喜包一份:<br/>
<#if givingItem?exists>
${pmg.itemMount}个${givingItem.name}<br/>
</#if>
<@gogame />
</p>
</card>
</wml>