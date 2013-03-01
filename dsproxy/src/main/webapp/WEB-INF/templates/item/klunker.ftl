<#-- 珠宝商人 -->
<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if npc?exists>${npc.name}：</#if><#if dialog?exists && dialog.dialog?exists && dialog.dialog != "" >${dialog.dialog}
<#else>
低级的碎片和宝石可以合成一个更高等级的宝石，合成一个需要1银.
</#if><br/>
<@a href="/p?i_LB/${pid}/${last_npc.id}/"/>买东西</a><br/>
<@a href="/p?i_LS/${pid}/1/0/"/>卖东西</a><br/>
<@a href="/p?i_LC/${pid}/${npc.id}/1/"/>宝石合成</a><br/>
<@a href="/p?i_LE/${pid}/${npc.id}/0/"/>宝石挖除</a><br/>
<@a href="/p?i_LR/${pid}/0/"/>修理装备</a><br/>
<@goback/>
<@gogame/>
</p>
</card>
</wml>