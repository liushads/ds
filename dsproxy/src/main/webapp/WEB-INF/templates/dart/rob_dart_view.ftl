<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<#include "award.ftl">
${label_other_name}${label_other_level}级<br/>
所属公会：<br/>
运送镖车：${label_name}<br/>
被劫次数：${label_rob_times}/${label_limit_times}<br/>
送达剩余：${label_less_time}<br/>
劫镖可获：
<#if label_award?exists>
	<@showAwart label_award/>
</#if>
劫镖所获需要到押镖主界面劫镖排行领取<br/>
<@a href="/p?d_RD/${pid}/${label_other_pid}/ok/"/>确定劫镖</a><br/>
<@goback/>
<@gogame/>
<#include "/include/foot_card.ftl">