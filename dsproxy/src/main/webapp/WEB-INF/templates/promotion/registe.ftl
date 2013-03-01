<#include "/include/header.ftl">
<card title="${game_title}"><p>
推荐登记：请填写你的推荐人ID<br/>
<input name="promoteId" maxlength="12" emptyok="false" type="text" /><br/>
<anchor>登记
	<@go href="/p?p_pp/${pid}/trd/" method="post"/>
		<postfield name="2" value="$promoteId" />
	</go>
</anchor>
<br/>
<br/>
<@goback/>
<@gogame/>
</p></card>
</wml>