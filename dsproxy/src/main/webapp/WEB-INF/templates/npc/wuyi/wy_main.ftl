<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if location?exists>
${location}<br/>
</#if>
<#if lastNpc?exists>
${lastNpc.name}:${lastNpc.description}<br/>
</#if>
<@a href="/p?n_Wy/${pid}/r/"/>活动介绍</a><br/>
<@a href="/p?n_Wy/${pid}/f/"/>合成劳动宝箱</a><br/>
<@a href="/p?n_Wy/${pid}/g/"/>领取劳动宝箱</a><br/>
<br/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>