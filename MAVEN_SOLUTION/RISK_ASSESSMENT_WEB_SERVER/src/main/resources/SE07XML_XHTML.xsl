<!--<?xml version="1.0" encoding="UTF-8"?>-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <!-- template SE07 -->
    <xsl:template match="/SE07">
        <html>
            <head>
                <title>Dados estatisticos</title>
            </head>
<body>Result: <xsl:value-of select="Result"/>
            </body>

        </html>
    </xsl:template>
    
    
</xsl:stylesheet>