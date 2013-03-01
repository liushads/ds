<#include "/include/header.ftl">
<card title="${game_title}"><p>
回复许愿<br/>
最多可写15个字<br/>
<#if pf?exists>
匿名：${pf.content}<br/>
</#if>
<input name="content" type="text" maxlength="15" emptyok="false" /><br/>
	<anchor>提交
		<@go href="/p?n_lw/${pid}/" method="post"/>
			<postfield name="1" value="reply" />
			<postfield name="2" value="${wishId}" />
			<postfield name="3" value="$content" />
	     </go>
	</anchor><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>