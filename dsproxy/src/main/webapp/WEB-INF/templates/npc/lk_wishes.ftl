<#include "/include/header.ftl">
<card title="${game_title}"><p>
扔许愿瓶<br/>
愿望最多可写15个字<br/>
<input name="content" type="text" maxlength="15" emptyok="false" /><br/>
	<anchor>提交
		<@go href="/p?n_lw/${pid}/" method="post"/>
			<postfield name="1" value="tw" />
			<postfield name="2" value="$content" />
	     </go>
	</anchor><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>