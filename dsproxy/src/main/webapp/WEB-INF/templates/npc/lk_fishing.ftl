<#include "/include/header.ftl">
<card title="${game_title}"><p>
钓鱼：<br/>
<#if fishingWishes?exists && fishingWishes?size &gt; 0>
你刚才扔出去的许愿瓶有了新的回复：<br/>
<#list fishingWishes as pf> 
		匿名:<@a href="/p?n_lw/${pid}/input/${pf.id}"/>${pf.content}</a><br/>
	</#list>
</#if>

<#if lure_note?exists>
${lure_note}<br/>
</#if>
<#if fish_note?exists>
${fish_note}<br/>
<#else>
你轻轻的放下了鱼线，鱼漂在海面上随着波澜慢慢的起伏；<br/>
</#if>
<@a href="/p?n_lw/${pid}/l"/>起勾</a><br/>
<@a href="/p?n_lw/${pid}/w"/>继续观察</a><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>