package com.twitter.crossbill

// TODO: This needs additional work
case class Module(
  typeSection: TypeSection, 
  importSection: ImportSection,
  functionSection: FunctionSection,
  exportSection: ExportSection,
  codeSection: CodeSection
)
