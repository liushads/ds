<#include "/include/header.ftl">
<card title="${game_title}">
<p>
你将使用Q币购买${goodsCount}个金贝,请点击
<anchor>这里
	<go href="${payUrl?xml}" method="post">
        <postfield name="info" value="${info}" />
    </go>
</anchor><br/>	
<@goback/>
</p></card>
</wml>