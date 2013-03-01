<#include "/include/header.ftl">
<card title="${game_title}">
<p>
请输入要保存的数量<br/>
<input type="text" name="num" value="${number}" format="*N" maxlength="5">1</input><br/>
<anchor>确定
	<@go href="/p?n_sr/${pid}/" method="post"/>
        <postfield name="1" value="save" />
        <postfield name="2" value="${itemid}" />
        <postfield name="3" value="${type}" />
        <postfield name="4" value="$num" />
    </go>
</anchor>
<@goback/>
<@gofacility/>
</p>
</card>
</wml>


 