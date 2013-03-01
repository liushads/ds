<#include "/include/header.ftl">
<card title="${game_title}"><p>
月牙村校场:<br/>
乐羊羊：<br/>
金牌10个、银牌10个、铜牌10个、收集齐这三种奖牌我可以给你兑换50银<br/>
你有剩余奖牌:<#if gold?exists>${gold}<#else>0</#if>金牌,<#if sliver?exists>${sliver}<#else>0</#if>银牌,<#if copper?exists>${copper}<#else>0</#if>铜牌.<br/>
<@a href="/p?n_xyy/${pid}/eq/"/>兑换</a><br/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>