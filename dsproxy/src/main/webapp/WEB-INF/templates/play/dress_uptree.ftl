<#include "/include/header.ftl">
<card title="${game_title}"><p>
<img alt="" src="${img_server}/tree_flower.gif" /><br/>
${player_item.item.name}<br/>
你现在拥有的数量为:${player_item.amount}<br/>
<#if num?exists>
请输入投放数量<br/>
<input name="itemnum" maxlength="12" emptyok="false" type="text" value=""/><br/>
<anchor>确定
	<@go href="/p?ply_Dun/${pid}/" method="post"/>
			<postfield name="1" value="${player_item.item.id}" />
			<postfield name="2" value="3" />
			<postfield name="3" value="$itemnum" />
	</go>
</anchor>	
<#else>
<@a href="/p?ply_Dun/${pid}/${player_item.item.id}/2"/>确定投放你行囊中所有${player_item.item.name}吗？</a><br/>
<@a href="/p?ply_Dun/${pid}/${player_item.item.id}/1"/>不，我要自己输入投放数量</a>
</#if>
<br/>
<@goback/>
<@gogame/>
</p></card>
</wml>
