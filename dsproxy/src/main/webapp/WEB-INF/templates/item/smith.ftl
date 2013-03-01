<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if npc?exists>${npc.name}：</#if><#if dialog?exists && dialog.dialog?exists && dialog.dialog != "" >${dialog.dialog}
<#else>
你好! 有什么可以为你帮忙的吗?
</#if><br/>
<#if last_npc.id?number==354><@a href="/p?i_LAI/${pid}/t/0"/>制药</a><br/></#if>
<@a href="/p?i_LB/${pid}/${last_npc.id}/"/>买东西</a><br/>
<@a href="/p?i_LS/${pid}/1/0/"/>卖东西</a><br/>
<@a href="/p?i_LR/${pid}/0/"/>修理装备</a><br/>
<br/>
<@gogame/>
</p>
</card>
</wml>