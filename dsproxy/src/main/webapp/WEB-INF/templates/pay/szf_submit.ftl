<#include "/include/header.ftl">
<card title="${game_title}">
<p>
请点击
<anchor>这里
	<go href="${payUrl?xml}" method="post">
        <postfield name="info" value="${info}" />
    </go>
</anchor>进行充值卡支付！<br/>	
<@goback/>
</p></card>
</wml>