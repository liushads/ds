<#include "/include/header.ftl">
<card title="${game_title}"><p>
道具赠送:<br/>
你已经成功赠送【${amount}】个【${playerItem.item.name}】给你的好友【${otherPlayer.name}】!<br/>
<br/>
<@a href="/p?i_SG/${pid}/${otherPlayer.id}/${itemType}"/>返回赠送列表</a>
</p>
</card>
</wml>