<#-- 门派特殊NPC -->
<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if npc?exists>${npc.name}：</#if><#if dialog?exists && dialog.dialog?exists && dialog.dialog != "" >${dialog.dialog}
<#else>
你好! 有什么可以为你帮忙的吗?
</#if><br/>
<@a href="/p?i_LF/${pid}/"/>装备铸造</a><br/>
<br/>
<@goback/>
<@gogame/>
</p>
</card>
</wml>