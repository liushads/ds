<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if location?exists>
${location}<br/>
</#if>
点泡泡赢取国庆节奖励<br/>
满50级，每天可免费点一次！<br/>
点泡泡赢取国庆节奖励，每次0.3金票！<br/>
<@a href="/p?n_Ly/${pid}/suiji/"/>随机点</a><br/>
<#assign x=12>
<#list 1..x as i>
	<img alt="" src="${img_server}/paopao${i}.png" /><@a href="/p?n_Ly/${pid}/d/${i}/"/>点</a>
  	<#if i % 3 == 0><br/></#if>
</#list>

<br/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>