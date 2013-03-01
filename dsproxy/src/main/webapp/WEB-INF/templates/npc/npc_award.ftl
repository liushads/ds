<#include "/include/header.ftl">
<card title="${game_title}"><p>
可领取的活动奖励：<br/>
<#if activeRewardList?exists>
<#list activeRewardList as reward>
<@a href="/p?n_IA/${pid}/detail/${reward.id}"/>${reward.activeDesc}</a><br/>
</#list>
<#elseif tongRewards?exists>
<#list tongRewards as reward>
<@a href="/p?n_IA/${pid}/show/${reward.id}"/>${reward.activeDesc}</a><br/>
</#list>
<#else>
你暂时没有奖励可以领取！<br/>
</#if>
<@gofacility/>
<@gogame/>
</p></card>
</wml>