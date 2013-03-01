<#include "/include/header.ftl">
<card title="${game_title}"><p>
灵异峡谷广场中央<br/>
小雪：任务完成了吗？ 我可以送你回月牙村校场!<br/>
任务进度:<#if isFinish==true>任务完成，请去交任务领奖励<#else>任务未完成</#if><br/>
任务状态:<#if monster901516?exists>幽灵(${monster901516}/10),</#if>
<#if monster901517?exists>吸血鬼(${monster901517}/10),</#if>
<#if monster901518?exists>狼人(${monster901518}/10),</#if>
<#if monster901519?exists>石像鬼(${monster901519}/10),</#if>
<#if monster901600?exists>木乃伊(${monster901600}/10),</#if>
<#if monster901601?exists>骷髅卫兵(${monster901601}/10)</#if>
<br/>
<@a href="/p?n_wsj/${pid}/t/3488/"/>离开灵异峡谷</a><br/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>