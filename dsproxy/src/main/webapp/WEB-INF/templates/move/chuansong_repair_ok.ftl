<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}传送使者：<br/>
<#if pitem?exists>
更换传送珠需要支付5银,
<#if pitem.currEndure==0>
您的传送珠剩余传送次数为0<br/>
<#else>
您的传送珠的传送次数还剩余${pitem.currEndure}次，更换后的传送珠的传送次数是未知的，您确定要现在要更换吗 ？<br/>
</#if>
<@a href="/p?mv_sz/${pid}/m/"/>立即更换</a>
</#if>
<br/>
<@gofacility/>
</p></card>
</wml>