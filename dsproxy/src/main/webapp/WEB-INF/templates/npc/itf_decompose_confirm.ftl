<#-- 显示可以合成的物品列表 -->
<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if item?exists>
分解大师:你确定要分解${item.name}？强化升星的装备也会被分解(绑定和交易中的除外)，需要消耗你的${item.name}和10银?<br/>
<@a href="/p?n_itf/${pid}/d/i/${item.id}/0/"/>确定</a><br/>
</#if>
<@goback/>
<@gofacility/>
<@gogame/>
</p>
</card>
</wml>