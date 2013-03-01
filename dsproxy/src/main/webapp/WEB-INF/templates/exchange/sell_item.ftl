<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if td?exists>
一键收摊成功<br/>
<#else>
现在将会把你选中的正在摆摊的所有物品全部收摊，你确认吗？<br/>
<@a href="/p?ex_VSA/${pid}/2"/>确定</a><br/>
</#if>

<@gogame/>
</p>
</card>
</wml>