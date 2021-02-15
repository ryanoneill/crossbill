package com.twitter.crossbill

// TODO: This needs additional work
case class Module(
  typeSection: TypeSection = TypeSection.Empty, 
  importSection: ImportSection = ImportSection.Empty,
  functionSection: FunctionSection = FunctionSection.Empty,
  tableSection: TableSection = TableSection.Empty,
  exportSection: ExportSection = ExportSection.Empty,
  codeSection: CodeSection = CodeSection.Empty
)
