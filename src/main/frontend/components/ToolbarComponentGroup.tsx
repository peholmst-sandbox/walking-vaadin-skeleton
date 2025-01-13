import { PropsWithChildren } from 'react';

export default function ToolbarComponentGroup(props: PropsWithChildren) {
  return <div className="flex flex-col items-stretch gap-s md:flex-row md:items-center">{props.children}</div>;
}
