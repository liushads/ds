<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if npc?exists>${npc.name}：</#if><#if dialog?exists && dialog.dialog?exists && dialog.dialog != "" >${dialog.dialog}
<#else>
你好! 有什么可以为你帮忙的吗?
</#if><br/>
<@a href="/p?i_Lp/${pid}/0/"/>装备打孔</a><br/>
<@a href="/p?i_LM/${pid}/"/>装备镶嵌</a><br/>
<@a href="/p?i_LPr/${pid}/"/>装备升星</a><br/>
<@a href="/p?i_LI/${pid}/"/>装备强化</a><br/>
<@a href="/p?i_LV/${pid}/"/>改变特性</a><br/>
<@a href="/p?n_itf/${pid}/c/1/"/>神器合成</a><br/>
<@a href="/p?n_itf/${pid}/d/"/>神器分解</a><br/>
<@a href="/p?i_Dh/${pid}/"/>装备挖孔</a><br/>
<@a href="/p?i_LD/${pid}/"/>装备分解</a><br/>
<@a href="/p?i_LFI/${pid}/w/0"/>装备锻造</a><br/>
<@a href="/p?i_LFI/${pid}/m/0"/>材料合成</a><br/>
<br/>
<@goback/>
<@gogame/>
</p>
</card>
</wml>