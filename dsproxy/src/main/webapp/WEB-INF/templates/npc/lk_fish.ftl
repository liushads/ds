<#include "/include/header.ftl">
<card title="${game_title}"><p>
钓鱼：<br/>
你轻轻的放下了鱼线，鱼漂在海面上随着波澜慢慢的起伏；<br/>
<#if lastLureId?exists && lastLureId &gt; 0>
<@a href="/p?n_lw/${pid}/l"/>起勾</a><br/>
<@a href="/p?n_lw/${pid}/w"/>继续观察</a><br/>
<#else>
<@a href="/p?n_lw/${pid}/f/l"/>挂鱼饵</a><br/>
<@a href="/p?n_lw/${pid}/tw/"/>扔许愿瓶</a><br/>
</#if>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>