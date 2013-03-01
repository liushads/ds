<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if curr_facility?exists>
	${curr_facility.name}<br/>
</#if>
${last_npc.name}:你好! 有什么可以为你效劳?<br/>
<@a href="/p?co_B1/${pid}/"/>成为师傅</a><br/>
<@a href="/p?co_LC/${pid}/"/>拜入师门</a><br/>
<@a href="/p?co_R1/${pid}/"/>脱离师门</a><br/>
<@a href="/p?co_I/${pid}/"/>了解详情</a><br/>
<@gofacility/>
</p></card>
</wml> 