<#include "/include/header.ftl">
<card title="${game_title}"><p>
月牙村校场:<br/>
乐羊羊：你已预测奖牌列表<br/>
金牌:<#if gold?exists>${gold}个<#else>0</#if><br/>
银牌:<#if sliver?exists>${sliver}个<#else>0</#if><br/>
铜牌:<#if copper?exists>${copper}个<#else>0</#if><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>