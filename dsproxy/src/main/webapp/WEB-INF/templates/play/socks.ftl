<#include "/include/header.ftl">
<card title="${game_title}"><p>
<img alt="" src="${img_server}/tree_flower.gif" /><br/>
圣诞袜子兑换：<br/>
<#if td?exists>
兑换成功<br/>
</#if>
5个圣诞绿袜子兑换1个圣诞老人<br/>
包裹<br/>
<@a href="/p?ply_Sk/${pid}/2/"/>兑换</a><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>