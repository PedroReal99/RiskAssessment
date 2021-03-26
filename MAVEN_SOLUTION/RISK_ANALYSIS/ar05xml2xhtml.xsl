<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="/ar05XHTMLTest2">
        <html>
            <head>
                <title>Validated cases by user</title>
            </head>
            <body>
                Result: <xsl:value-of select="Result"/>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>