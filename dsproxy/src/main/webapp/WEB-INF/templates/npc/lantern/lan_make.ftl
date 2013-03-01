<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
元宵节活动做元宵<br/>
收集好各种需要的材料你就可以做一个美味的元宵<br/>
<#list itemList as item>
	<@a href="/p?i_LCM/${pid}/${item.item.id}/849/"/>合成</a>  <@showItemNoBuy item.item/><br/>
</#list>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>