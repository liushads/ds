<#include "/include/header.ftl">
<card title="${game_title}"><p>
购买会员<br/>
你选择了<#if payType?exists && payType=='1'>包月</#if><#if payType?exists && payType=='2'>包年</#if>会员服务，需要你支付<#if payType?exists && payType=='1'>10</#if><#if payType?exists && payType=='2'>100</#if>金<br/>
<@a href="/p?p_m/${pid}/b/${payType}/c/"/>确定购买</a><br/>
<@goback/>
<@gogame/>
</p></card>
</wml>